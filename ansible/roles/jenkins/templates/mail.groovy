import jenkins.model.*

def jenkinsLocationConfiguration = JenkinsLocationConfiguration.get()

jenkinsLocationConfiguration.setAdminAddress("Isabelle/Jenkins <ci@isabelle.systems>")
jenkinsLocationConfiguration.setUrl("https://{{ config.domains.jenkins }}/jenkins")
jenkinsLocationConfiguration.save()

def inst = Jenkins.getInstance()

def desc = inst.getDescriptor("hudson.tasks.Mailer")

desc.setSmtpHost("mailrelay.informatik.tu-muenchen.de")
desc.setUseSsl(false)

desc.save()
