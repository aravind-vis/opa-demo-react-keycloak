package policies.authz 

 acl_config = {"DATA_SCIENTIST": ["VIEW_PROJECT","DELETE_PROJECT"], "REVIEWER" : ["VIEW_PROJECT"]}

test_allowed_for_owner {
    allow with input as { "owner": "chella", "user" : "chella" } with data.acls as acl_config
}

test_not_allowed_for_unshared_user {
    not allow with input as { "owner": "chella", "user" : "aravind" } with data.acls as acl_config
}

test_allowed_for_user_with_inherent_role_and_shared_with {
    allow with input as { "owner": "chella", "user" : "aravind", "operation" : "DELETE_PROJECT","role" : "DATA_SCIENTIST","shared_with": "DATA_SCIENTIST" } with data.acls as acl_config
}

test_allowed_for_user_with_inherent_role_and_not_shared_with {
    not allow with input as { "owner": "chella", "user" : "aravind", "operation" : "DELETE_PROJECT","role" : "DATA_SCIENTIST","shared_with": "REVIEWER" } with data.acls as acl_config
}