OpenNebula
==========

*OpenNebula* is the management toolkit for virtual machines hosted by LRZ.
The web interface is called *Sunstone* and can be reached under `www.cloud.mwn.de <https://www.cloud.mwn.de>`_.
To obtain access, you need to contact our chair administrators.


Templates
---------

Virtual machines can be instantiated from a *template*.
Templates specify the operating system image, hard disk, network adapter, CPU cores, and memory.

.. note::

  In our specific setup, we use one of the ``Isabelle Worker`` template.

Updating a template does not change existing virtual machines.


Virtual machines
----------------

A virtual machine must not have more than 8 cores.

.. note::

  In our specific setup, our quota is 64 cores total.
  We allocate at most 8 cores for one machine; hence, we can run at least 8 machines.

Worker machines can be created and destroyed very easily.
After instantiation, Sunstone will display a new VM in the ``PENDING`` and later, in the ``PROLOG`` state.
Machines can stay in that state for a while Sunstone is deploying it to a physical host.
Later it progresses to the ``BOOT`` phase, at which point it displays its IP address.
Add that IP address to the correct inventory file.
