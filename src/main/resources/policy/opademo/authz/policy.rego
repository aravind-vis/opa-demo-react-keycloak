package opademo.authz

default allow = false

allow{
    operation := input.operation
	operation == "CREATE_PROJECT"
    "ROLE_CREATOR" == input.role[_]
}
