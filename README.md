# Isabelle CI infrastructure
This is the infrastructure-as-code repository for the Isabelle infrastructure, hosted at TUM.

## Quick start

1. Make sure `python` and `virtualenv` are installed.
2. In this repository, run `virtualenv venv`.
3. Activate the virtual environment: `source venv/bin/activate`
4. Install the dependencies: `pip install -r requirements.txt`

## Preliminaries
Recommended reading for this documentation:
- [Ansible documentation](https://docs.ansible.com/ansible/), especially the following sections:
    * [Introduction](https://docs.ansible.com/ansible/)_
    * [Playbooks](https://docs.ansible.com/ansible/playbooks.html)
    * [Playbooks: Special Topics/Tags](https://docs.ansible.com/ansible/playbooks_tags.html)
- [Lehrstuhl-VM (de)](https://wiki.in.tum.de/Informatik/Benutzerwiki/LehrstuhlVM)

## Architecture
The purpose of this configuration is to bootstrap a set of machines to a defined state.
Bootstrapping is idempotent: running it twice on the same machines is the same as running it once.
In most cases, it is also incremental: if change it monotonically, we can re-apply bootstrapping and get the same result as bootstrapping a machine from a pristine state.
The only assumption is that we never manually configure the machines, i.e. logging in via SSH and performing arbitrary commands in Bash.

There are of course exceptions to this rule, for example in emergencies (downtime).


### Overview

There are three kinds of servers:

1. The central server is the *leader*, which runs on a low-resource virtual machine.
   It mainly hosts an Apache web server and the Jenkins instance.
   It performs regular polling of the repositories and starts and coordinates builds on the workers.

2. *Workers* require a lot of resources.
   There are various kinds of workers with different configuration based on hosting provider and OS platform.

3. The *AFP submission* system is a single, low-end machine.
   The leader acts as a proxy for that machine.


### Components

#### Machines
The contact for all machines are the Chair admins (MTAs).
Hostnames are reachable from within the institute by hostname, from the outside via `.in.tum.de`/`.cit.tum.de` domain
(e.g., `hpc.isabelle.in.tum.de`).

|                      | Hosted by    | Spec                                                            | Accounts    | Hostnames                                                                                              |
|----------------------|--------------|-----------------------------------------------------------------|-------------|--------------------------------------------------------------------------------------------------------|
| Leader               | RBG          | dual-core VM running Ubuntu 18.04 LTS, virtualized by vSphere   | LDAP        | `vmnipkow13`<br> `ci.isabelle.systems`<br> `isa-afp.org`<br> `www.isa-afp.org`<br> `devel.isa-afp.org` |
| Linux/MTA worker     | Chair admins | various high-end machines running Ubuntu 20.04, not virtualized | LDAP        | `lxcisa1`, `hpc.isabelle`                                                                              |
| macOS/MTA            | Chair admins | high-end iMac servers (mac minis + mac studio), not virtualized | LDAP        | `mini{1-3}`, `studio1`                                                                                 |
| AFP machine          | Printer room | Workstation running Ubuntu 22.04 LTS, not virtualized           | Local admin | `atnipkow8`                                                                                            |
| Cluster workstations | Printer room | High-end workstation running Ubuntu 22.04 LTS, not virtualized  | LDAP        | `of{1-4}`                                                                                              |
| Cluster servers      | Chair admins | High-end servers running Ubuntu 22.04 LTS, not virtualized      | LDAP        | `se{1-4}`                                                                                              |


#### External Domains
|                    | Hosted by      | Administration |
|--------------------|----------------|----------------|
| `isabelle.systems` | 1&1            | Lars Hupel     |
| `isa-afp.org`      | United Domains | Gerwin Klein   |

#### SSL certificates
Either local RBG certificates or Let's Encrypt.


## Ansible
Ansible is an open-source *configuration management system*.
Its main concepts are:
- inventories
- hosts
- playbooks
- roles
- tasks

### Hosts and inventories
*Hosts* are machines which are to be configured by Ansible.
They are specified by a host name or an IP address.
*Inventories* are lists of hosts.
Hosts can be grouped together; groups may form a hierarchy.
In an inventory file, hosts or groups of hosts may have additional variables.
Inventory files use a simple, INI-like syntax.

Example:

```
[leader-vm]
ci-staging.isabelle.systems

[worker-vm]
10.155.208.95 worker_name=worker0-testing

[afp-vm]
10.155.208.96

[testing:children]
leader-vm
worker-vm
afp-vm
```

This inventory specifies three groups of hosts: ``leader-vm``, ``worker-vm``, and ``afp-vm``.
It also demonstrates how to specify a variable assignment for a host.
Additionally, it specifies the ``testing`` group which encompasses all hosts in this file.

Ansible connects to hosts via SSH from your local machine.
Hence, if you are running Ansible, all hosts must be reachable from your local machine and you must have proper credentials (e.g., SSH private keys).

### Playbooks, roles, and tasks
A *playbook* assigns *roles* to hosts.
A role is a list of *tasks*.
Roles should correspond to high-level concepts of what services a machine should offer.

The following example is a simplified version of the actual playbook:
```
- name: configure workers
  hosts: worker-vm
  roles:
  - worker
```
Playbooks use YAML syntax.

### Executing Ansible
To run Ansible, you need to give it an inventory file and a playbook:
```
ansible-playbook -i inventories/production [-l GROUP|HOST] site.yml
```
In this repository, there are two inventory files: one for production, one for testing.
Always check the contents of the inventory files before deploying.

## Processes

### Security updates
Security updates should be applied regularly.
The most critical part of the infrastructure is the Jenkins instance which is exposed to the Internet.
It is highly recommended to follow the `Jenkins Security Advisories <https://wiki.jenkins-ci.org/display/JENKINS/Security+Advisories>`_.

### Isabelle Version Update
After Isabelle release:
1. Create [linter](https://github.com/isabelle-prover/isabelle-linter) release

After AFP release.
1. Update versions in [group vars](ansible/group_vars/all.yml)
2. Re-deploy `afp`

## Jenkins
Server is controlled with systemd, e.g. can be stopped with: `service jenkins stop`.