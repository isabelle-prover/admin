// https://github.com/jenkinsci/puppet-jenkins/blob/master/files/puppet_helper.groovy

import com.cloudbees.jenkins.plugins.sshcredentials.impl.*
import com.cloudbees.plugins.credentials.*
import com.cloudbees.plugins.credentials.common.*
import com.cloudbees.plugins.credentials.domains.*
import com.cloudbees.plugins.credentials.impl.*
import hudson.model.*;
import hudson.plugins.sshslaves.*;
import hudson.util.*;
import jenkins.model.*
import jenkins.model.*;
import jenkins.security.*;
import org.jenkinsci.plugins.*;

global_domain = Domain.global()
credentials_store =
  Jenkins.instance.getExtensionList(
    'com.cloudbees.plugins.credentials.SystemCredentialsProvider'
  )[0].getStore()

credentials = new BasicSSHUserPrivateKey(
  CredentialsScope.GLOBAL,
  "{{ config.jenkins.credentials_uuid }}",
  "jenkins",
  new BasicSSHUserPrivateKey.FileOnMasterPrivateKeySource("/home/admin/jenkins-worker"),
  "",
  "jenkins-worker"
)

def username_matcher = CredentialsMatchers.withUsername("jenkins")

def available_credentials =
  CredentialsProvider.lookupCredentials(
    StandardUsernameCredentials.class,
    Jenkins.getInstance(),
    hudson.security.ACL.SYSTEM,
    new SchemeRequirement("ssh")
  )

def existing_credentials = CredentialsMatchers.firstOrNull(available_credentials, username_matcher)

if (existing_credentials != null) {
  credentials_store.updateCredentials(
    global_domain,
    existing_credentials,
    credentials
  )
}
else {
  credentials_store.addCredentials(global_domain, credentials)
}
