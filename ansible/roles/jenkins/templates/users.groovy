user = hudson.model.User.get('admin')
user.setFullName('Administrative User')
email = new hudson.tasks.Mailer.UserProperty('info@isabelle.systems')
user.addProperty(email)
password = hudson.security.HudsonPrivateSecurityRealm.Details.fromPlainPassword('{{ config.jenkins.password }}')
user.addProperty(password)
user.save()

jenkins = jenkins.model.Jenkins.getInstance()
jenkins.getRootPath().child("secrets/initialAdminPassword").delete()

if (!jenkins.installState.isSetupComplete()) {
    jenkins.install.InstallState.INITIAL_SETUP_COMPLETED.initializeState()
}