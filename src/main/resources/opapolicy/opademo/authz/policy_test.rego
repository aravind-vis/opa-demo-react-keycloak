package opademo.authz

acl_data = { "DATA_SCIENTIST": ["VIEW_PROJECT", "DELETE_PROJECT", "UPDATE_PROJECT", "SHARE_PROJECT"], "REVIEWER": ["VIEW_PROJECT"]}

test_allowed_for_user_with_inherent_role_and_shared_with {
    allow with input as { "owner": "chella", "user" : "aravind", "operation" : "SHARE_PROJECT","projectRole" : ["DATA_SCIENTIST"],"userRole": ["DATA_SCIENTIST"] } with data.opademo.roles.permissions.acls as acl_data
}


test_not_allowed_for_user_without_inherent_role_and_shared_with {
    not allow with input as { "owner": "chella", "user" : "aravind", "operation" : "SHARE_PROJECT","projectRole" : ["DATA_SCIENTIST"],"userRole": ["REVIEWER"]} with data.opademo.roles.permissions.acls as acl_data
}
