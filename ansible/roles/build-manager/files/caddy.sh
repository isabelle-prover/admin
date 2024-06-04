#!/bin/bash
set -o nounset
set -o errexit

install --mode 0600 "$1".privkey.pem /var/lib/caddy/.local/share/caddy/tls/key.pem
install --mode 0644 "$1".fullchain.pem /var/lib/caddy/.local/share/caddy/tls/fullchain.pem

systemctl restart caddy
