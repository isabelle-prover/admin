import jenkins.model.*

def inst = Jenkins.instance

def c = com.smartcodeltd.jenkinsci.plugins.buildmonitor.BuildMonitorDescriptor
def desc = inst.getDescriptorByType(c)

desc.setPermissionToCollectAnonymousUsageStatistics(false)

inst.save()
