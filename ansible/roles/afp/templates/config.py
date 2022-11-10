# Config file for CGI scripts and submission checker

UPLOAD_DIR = "/var/afpbuild/up"
DOWNLOAD_DIR = "/var/afpbuild/down"

LINKBASE = "https://{{ config.domains.afp }}/webapp/"
AFPLINKBASE = "https://{{ config.domains.afp }}/"

FROM = "AFP Submission System <afp@isabelle.systems>"
SENDER = "{{ config.mail.sender }}"
ADMINS = ["afp@isabelle.systems"]
EDITORS = ["afp-submit@in.tum.de"]
TEMPLATES = "/opt/afpbuild/src/templates"

POLLTIME = 0.3

MAILSSL = True
MAILSERVER = "{{ config.mail.host }}"
MAILSERVERPORT = {{ config.mail.port }}
MAILUSER = "{{ config.mail.user }}"
MAILPASS = "{{ config.mail.password }}"

BASE_CONTAINER = "base"
UNPRIV_USER = "ubuntu"
CONTAINER_DIR = "/home/" + UNPRIV_USER
THEORY_DIR = CONTAINER_DIR + "/thy"
ISABELLE_PATH = "/opt/Isabelle{{ config.isabelle.version }}/bin/isabelle"
ISABELLE_SETTINGS = ["-o", "document=pdf",
                     "-o", "document_variants=document:outline=/proof,/ML",
                     "-o", "browser_info",
                     "-o", "timeout_scale=4",
                     "-v"]
ISABELLE_BROWSER_INFO = CONTAINER_DIR + "/.isabelle/Isabelle{{ config.isabelle.version }}/browser_info/"

CONTAINERS_PATH = "/media/data/conrad/.local/share/lxc/"
CONTAINER_ROOT = "overlay/delta"

BROWSER_INFO_DIR = "/var/www/afp/browser_info"
BROWSER_INFO_WWW = "browser_info"

DEBUG = True
CLEANUP_CONTAINER = True
