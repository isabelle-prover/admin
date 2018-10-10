#!/bin/sh
/home/admin/cli.sh connect-node "{{ hostvars[item]['worker_name'] }}" &> /dev/null
