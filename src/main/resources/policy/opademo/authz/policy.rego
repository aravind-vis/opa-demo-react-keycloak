package opademo.authz

default allow = false

allow{
    operation := input.operation
	operation == "CREATE_PROJECT"
    "ROLE_CREATOR" == input.role[_]
}

allow{
    operation := input.operation
    operation == "SHARE_PROJECT"
    user := input.user
    user == input.owner
}
