package policies.authz 

default allow = false

# Allow admins to do anything.
allow {
	user_is_owner
}

user_is_owner {
   owner := input.owner
   input.user == owner
}

allow {
  user_role_has_access
}

user_role_has_access {
 data.acls[input.role][_] == input.operation
 data.acls[input.shared_with][_] == input.operation
}