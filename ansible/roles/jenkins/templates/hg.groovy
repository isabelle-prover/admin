import jenkins.model.*

def inst = Jenkins.instance

def c = hudson.plugins.mercurial.MercurialInstallation.DescriptorImpl.class
def desc = inst.getDescriptorByType(c)

def empty = new java.util.LinkedList()

def hg = new hudson.plugins.mercurial.MercurialInstallation(
  "cached-mercurial",
  "",
  "/usr/bin/hg",
  false,
  true,
  true,
  "",
  empty
)

desc.setInstallations(hg)

inst.save()
