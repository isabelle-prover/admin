org.jenkinsci.main.modules.sshd.SSHD.get().setPort(-1)

realm = new hudson.security.HudsonPrivateSecurityRealm(false, false, null)

strategy = new hudson.security.FullControlOnceLoggedInAuthorizationStrategy()

crumbs = new hudson.security.csrf.DefaultCrumbIssuer(false)

jenkins = jenkins.model.Jenkins.getInstance()
jenkins.setSecurityRealm(realm)
jenkins.setAuthorizationStrategy(strategy)
jenkins.setCrumbIssuer(crumbs)
jenkins.save()
