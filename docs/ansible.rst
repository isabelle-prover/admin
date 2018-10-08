Ansible
=======

Ansible is an open-source *configuration management system*.
Its main concepts are:

- inventories
- hosts
- playbooks
- roles
- tasks


Hosts and inventories
---------------------

*Hosts* are machines which are to be configured by Ansible.
They are specified by a host name or an IP address.
*Inventories* are lists of hosts.
Hosts can be grouped together; groups may form a hierarchy.
In an inventory file, hosts or groups of hosts may have additional variables.
Inventory files use a simple, INI-like syntax.

Example:

::

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

This inventory specifies three groups of hosts: ``leader-vm``, ``worker-vm``, and ``afp-vm``.
It also demonstrates how to specify a variable assignment for a host.
Additionally, it specifies the ``testing`` group which encompasses all hosts in this file.

Ansible connects to hosts via SSH from your local machine.
Hence, if you are running Ansible, all hosts must be reachable from your local machine and you must have proper credentials (e.g., SSH private keys).


Playbooks, roles, and tasks
---------------------------

A *playbook* assigns *roles* to hosts.
A role is a list of *tasks*.
Roles should correspond to high-level concepts of what services a machine should offer.

The following example is a simplified version of the actual playbook:

::

  - name: configure workers
    hosts: worker-vm
    roles:
      - worker

Playbooks use YAML syntax.


Executing Ansible
-----------------

To run Ansible, you need to give it an inventory file and a playbook:

::

  $ ansible-playbook -i inventories/production [-l GROUP|HOST] site.yml

In this repository, there are two inventory files: one for production, one for testing.
Always check the contents of the inventory files before deploying.
