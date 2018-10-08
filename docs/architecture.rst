Architecture
============

The purpose of this configuration is to bootstrap a set of machines to a defined state.
Bootstrapping is idempotent: running it twice on the same machines is the same as running it once.
In most cases, it is also incremental: if change it monotonically, we can re-apply bootstrapping and get the same result as bootstrapping a machine from a pristine state.
The only assumption is that we never manually configure the machines, i.e. logging in via SSH and performing commands in Bash.

.. note::

  There are of course exceptions to this rule, for example in emergencies (downtime).


Overview
--------

The central server is the *Jenkins leader*, which runs on a low-resource virtual machine.
It mainly hosts a web server and the Jenkins instance.
It performs regular polling of the repositories and starts and coordinates builds on the *Jenkins workers*.
Workers require a lot of resources.


Components
----------

Leader machine
~~~~~~~~~~~~~~

Hosted by
  Rechnerbetriebsgruppe der Fakultäten für Mathematik und Informatik

Specification
  dual-core VM running Ubuntu 14.04 LTS, virtualized by vSphere

Hostnames
  - ``ci.isabelle.systems`` (alias for ``vmnipkow7.informatik.tu-muenchen.de``)
  - ``www.isa-afp.org`` (alias for ``vmnipkow7.informatik.tu-muenchen.de``)

Administration
  Full root access (accounts can be obtained from Lars Hupel)

Contact
  Requests should always be directed to our chair administrators


Worker machines
~~~~~~~~~~~~~~~

Hosted by
  Leibniz-Rechenzentrum

Specification
  octa-core VMs running Debian 8, virtualized by OpenNebula/KVM

Hostnames
  various, see :doc:`opennebula`

Administration
  - Full root access (accounts can be obtained from Lars Hupel)
  - Full VM control (accounts for OpenNebula can be obtained from the chair administrators)

Contact
  LRZ Service Desk


AFP machine
~~~~~~~~~~~

Hosted by
  Leibniz-Rechenzentrum

Specification
  dual-core VM running Ubuntu 14.04 LTS, virtualized by OpenNebula/KVM

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
  Chair administrators

Administration
  Full push access (accounts can be obtained from Lars Hupel)

Contact
  Chair administrators


Domain ``isabelle.systems``
~~~~~~~~~~~~~~~~~~~~~~~~~~~

Hosted by
  Domainfactory

Administration
  Lars Hupel


Domain ``isa-afp.org``
~~~~~~~~~~~~~~~~~~~~~~

Hosted by
  United Domains

Administration
  Gerwin Klein


SSL certificate ``isabelle.systems``
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Created by
  StartSSL

Administration
  Lars Hupel
