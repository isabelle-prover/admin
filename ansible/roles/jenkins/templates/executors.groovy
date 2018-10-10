// http://pghalliday.com/jenkins/groovy/sonar/chef/configuration/management/2014/09/21/some-useful-jenkins-groovy-scripts.html

import jenkins.model.*

def instance = Jenkins.getInstance()

instance.setNumExecutors(4)

instance.save()
