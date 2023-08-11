import com.michelin.cio.hudson.plugins.rolestrategy.AuthorizationType
import com.michelin.cio.hudson.plugins.rolestrategy.PermissionEntry
import com.michelin.cio.hudson.plugins.rolestrategy.RoleBasedAuthorizationStrategy
import com.michelin.cio.hudson.plugins.rolestrategy.Role
import com.synopsys.arc.jenkins.plugins.rolestrategy.RoleType

org.jenkinsci.main.modules.sshd.SSHD.get().setPort(-1)

realm = new hudson.security.PAMSecurityRealm("sshd")

strategy = new RoleBasedAuthorizationStrategy()

// Create roles
adminRole = new Role('admin', hudson.security.Permission.getAll().toSet())
buildRole = new Role('build', [hudson.model.Item.BUILD, hudson.model.Item.CANCEL] as Set)
readRole = new Role('read', [jenkins.model.Jenkins.READ, hudson.model.Item.READ, hudson.model.Item.DISCOVER] as Set)
strategy.addRole(RoleType.Global, adminRole)
strategy.addRole(RoleType.Global, buildRole)
strategy.addRole(RoleType.Global, readRole)

// Assign roles to users/groups
strategy.assignRole(RoleType.Global, adminRole, new PermissionEntry(AuthorizationType.USER, 'admin'))
strategy.assignRole(RoleType.Global, readRole, new PermissionEntry(AuthorizationType.GROUP, 'isabelle'))
strategy.assignRole(RoleType.Global, buildRole, new PermissionEntry(AuthorizationType.GROUP, 'isabelle'))
strategy.assignRole(RoleType.Global, readRole, new PermissionEntry(AuthorizationType.USER, 'anonymous'))


crumbs = new hudson.security.csrf.DefaultCrumbIssuer(false)

jenkins = jenkins.model.Jenkins.getInstance()
jenkins.setSecurityRealm(realm)
jenkins.setAuthorizationStrategy(strategy)
jenkins.setCrumbIssuer(crumbs)
jenkins.save()
