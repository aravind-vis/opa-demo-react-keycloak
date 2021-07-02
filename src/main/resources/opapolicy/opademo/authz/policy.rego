package opademo.authz


default allow = false

allow{
    operation := input.operation
	operation == "CREATE_PROJECT"
    "ROLE_CREATOR" == input.role[_]
}

allow{
    operation := input.operation
    user := input.user
    user == input.owner
}


allow {
    operation := input.operation
    some i
    some j
    input.projectRole[i] == input.userRole[j]
    data.opademo.roles.permissions.acls[input.projectRole[i]][_] == operation
    data.opademo.roles.permissions.acls[input.userRole[j]][_] == operation
}
