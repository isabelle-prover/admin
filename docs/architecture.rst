Architecture
============

The purpose of this configuration is to bootstrap a set of machines to a defined state.
Bootstrapping is idempotent: running it twice on the same machines is the same as running it once.
In most cases, it is also incremental: if change it monotonically, we can re-apply bootstrapping and get the same result as bootstrapping a machine from a pristine state.
The only assumption is that we never manually configure the machines, i.e. logging in via SSH and performing arbitrary commands in Bash.

.. note::

  There are of course exceptions to this rule, for example in emergencies (downtime).


Overview
--------

There are three kinds of servers:

1. The central server is the *leader*, which runs on a low-resource virtual machine.
   It mainly hosts an Apache web server and the Jenkins instance.
   It performs regular polling of the repositories and starts and coordinates builds on the workers.

2. *Workers* require a lot of resources.
   There are various kinds of workers with different configuration based on hosting provider and OS platform.

3. The *AFP submission* system is a single, low-end machine.
   The leader acts as a proxy for that machine.


Components
----------

Leader machine
~~~~~~~~~~~~~~

Hosted by
  Rechnerbetriebsgruppe der Fakultäten für Mathematik und Informatik

Specification
  dual-core VM running Ubuntu 18.04 LTS, virtualized by vSphere

Hostnames
  - ``vmnipkow13.informatik.tu-muenchen.de``
  - ``ci.isabelle.systems``
  - ``isa-afp.org``
  - ``www.isa-afp.org``
  - ``devel.isa-afp.org``

Administration
  Full root access (accounts can be obtained from Lars Hupel)

Contact
  Requests should always be directed to our chair administrators


Worker machines
~~~~~~~~~~~~~~~

Linux/LRZ
.........

Hosted by
  Leibniz-Rechenzentrum

Specification
  octa-core VMs running Ubuntu 16.04 LTS, virtualized by OpenNebula/KVM

Hostnames
  various, see :doc:`opennebula`

Administration
  - Full root access (accounts can be obtained from Lars Hupel)
  - Full VM control (accounts for OpenNebula can be obtained from the chair administrators)

Contact
  LRZ Service Desk

Linux/MTA
.........

Hosted by
  chair administrators

Specification
  various high-end machines running Ubuntu 16.04 LTS and 18.04 LTS, not virtualized

Hostnames
  various, see :doc:`mta`

Administration
  Full root access (accounts can be obtained from Lars Hupel)

Contact
  chair administrators

macOS/MTA
.........

Hosted by
  chair administrators

Specification
  low-end iMac running macOS El Capitan, not virtualized

Hostnames
  ``macisa1.informatik.tu-muenchen.de``

Administration
  Full root access (accounts can be obtained from Lars Hupel)

Contact
  chair administrators

AFP machine
~~~~~~~~~~~

Hosted by
  Leibniz-Rechenzentrum

Specification
  dual-core VM running Ubuntu 16.04 LTS, virtualized by OpenNebula/KVM

Hostnames
  various, see :doc:`opennebula`

Administration
  - Full root access (accounts can be obtained from Lars Hupel)
  - Full VM control (accounts for OpenNebula can be obtained from the chair administrators)

Contact
  LRZ Service Desk


Documentation
~~~~~~~~~~~~~

Hosted by
  `GitHub <https://github.com/isabelle-prover/admin>`_/Read the Docs

Administration
  Full push access (accounts can be obtained from Lars Hupel)


Domain ``isabelle.systems``
~~~~~~~~~~~~~~~~~~~~~~~~~~~

Hosted by
  1 & 1

Administration
  Lars Hupel


Domain ``isa-afp.org``
~~~~~~~~~~~~~~~~~~~~~~

Hosted by
  United Domains

Administration
  Gerwin Klein


SSL certificates
~~~~~~~~~~~~~~~~

Created by
  Let's Encrypt
