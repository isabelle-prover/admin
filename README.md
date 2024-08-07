# Isabelle Infrastructure Administration
This is the infrastructure-as-code repository for the Isabelle infrastructure, hosted at TUM.

## Quick start

1. Make sure `python` and `virtualenv` are installed.
2. In this repository, run `virtualenv venv`.
3. Activate the virtual environment: `source venv/bin/activate`
4. Install the dependencies: `pip install -r requirements.txt`

## Preliminaries
Recommended reading for this documentation:
- [Ansible documentation](https://docs.ansible.com/ansible/), especially the following sections:
    * [Introduction](https://docs.ansible.com/ansible/)
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

There are different kinds of servers:

1. The central server is the *leader*, which runs on a low-resource virtual machine.
   It mainly hosts an Apache web server.

2. *cluster-node* require a lot of resources.
   There are various kinds of workers with different configuration based on hosting provider and OS platform.

3. *Build manager* is a main cluster node, which also runs the main isabelle process and the database.

3. The *AFP submission* system is a single, medium-powered machine.
   The leader acts as a proxy for that machine.


### Components

#### Machines
The contact for all machines are the Chair admins (MTAs).
Hostnames are reachable from within the institute by hostname, from the outside via `.in.tum.de`/`.cit.tum.de` domain
(e.g., `hpc.isabelle.in.tum.de`).

|                        | Hosted by    | Spec                                                            | Accounts    | Hostnames                                                                    |
|------------------------|--------------|-----------------------------------------------------------------|-------------|------------------------------------------------------------------------------|
| AFP                    | RBG          | dual-core VM running Ubuntu 18.04 LTS, virtualized by vSphere   | LDAP        | `vmnipkow13`<br> `isa-afp.org`<br> `www.isa-afp.org`<br> `devel.isa-afp.org` |
| Linux/MTA worker       | Chair admins | various high-end machines running Ubuntu 20.04, not virtualized | LDAP        | `lxcisa1`, `hpc.isabelle`                                                    |
| macOS/MTA              | Chair admins | high-end iMac servers (mac minis + mac studio), not virtualized | LDAP        | `mini{1-3}`, `studio1`                                                       |
| AFP-Submission machine | Printer room | Workstation running Ubuntu 22.04 LTS, not virtualized           | Local admin | `atnipkow8`, `afp-submit.proof`                                              |
| Cluster workstations   | Printer room | High-end workstation running Ubuntu 22.04 LTS, not virtualized  | LDAP        | `of{1-4}`                                                                    |
| Cluster servers        | Chair admins | High-end servers running Ubuntu 22.04 LTS, not virtualized      | LDAP        | `se{1-4}`, `build.proof`                                                     |

#### Mailing Lists
Note: tum lists use `@mailman46.in.tum.de` in their FROM.

|                         | Used for                 | Run by |
|-------------------------|--------------------------|--------|
| `afp-submit@in.tum.de`  | AFP editor discussion    | MTAs   |
| `isabelle-ci@in.tum.de` | build manager to post on | MTAs   |

#### External Domains
|                    | Hosted by      | Administration |
|--------------------|----------------|----------------|
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
See [docs](https://docs.ansible.com/ansible/latest/inventory_guide/intro_inventory.html) for mor details.

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
Always check the contents of the inventory files before deploying.

## Processes

### Security updates
Security updates should be applied regularly.
The most critical part of the infrastructure is the Isabelle build manager instance which is exposed to the Internet.

### Isabelle Version Update
After Isabelle release:
1. Create [linter](https://github.com/isabelle-prover/isabelle-linter) release

After AFP release.
1. Update versions in [group vars](ansible/group_vars/all.yml)
2. Re-deploy `afp`
