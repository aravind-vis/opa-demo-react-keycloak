import React, { useEffect, useState } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './blog.reducer';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import Switch from '../ui/Switch';

export const Blog = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const blogList = useAppSelector(state => state.blog.entities);
  const loading = useAppSelector(state => state.blog.loading);
  const [admin, setAdmin] = useState(false);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  const viewButton = blog => {
    return (
      <Button tag={Link} to={`${match.url}/${blog.id}`} color="info" size="sm" data-cy="entityDetailsButton">
        <FontAwesomeIcon icon="eye" />{' '}
        <span className="d-none d-md-inline">
          <Translate contentKey="entity.action.view">View</Translate>
        </span>
      </Button>
    );
  };

  const editButton = blog => {
    return (
      <Button tag={Link} to={`${match.url}/${blog.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
        <FontAwesomeIcon icon="pencil-alt" />{' '}
        <span className="d-none d-md-inline">
          <Translate contentKey="entity.action.edit">Edit</Translate>
        </span>
      </Button>
    );
  };

  const deleteButton = blog => {
    return (
      <Button tag={Link} to={`${match.url}/${blog.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
        <FontAwesomeIcon icon="trash" />{' '}
        <span className="d-none d-md-inline">
          <Translate contentKey="entity.action.delete">Delete</Translate>
        </span>
      </Button>
    );
  };

  console.log(' checming is admin ' + admin);

  return (
    <div>
      <div style={{ display: 'flex', justifyContent: 'flex-end', alignItems: 'center' }}>
        <Switch isOn={admin} handleToggle={() => setAdmin(!admin)} onColor="#EF476F" />
      </div>
      <h2 id="blog-heading" data-cy="BlogHeading">
        <Translate contentKey="opademoreactkeycloakApp.blog.home.title">Blogs</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="opademoreactkeycloakApp.blog.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="opademoreactkeycloakApp.blog.home.createLabel">Create new Blog</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {blogList && blogList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="opademoreactkeycloakApp.blog.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="opademoreactkeycloakApp.blog.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="opademoreactkeycloakApp.blog.handle">Handle</Translate>
                </th>
                <th>
                  <Translate contentKey="opademoreactkeycloakApp.blog.user">User</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {blogList.map((blog, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${blog.id}`} color="link" size="sm">
                      {blog.id}
                    </Button>
                  </td>
                  <td>{blog.name}</td>
                  <td>{blog.handle}</td>
                  <td>{blog.user ? blog.user.login : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      {viewButton(blog)}
                      {admin ? editButton(blog) : null}
                      {admin ? deleteButton(blog) : null}
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="opademoreactkeycloakApp.blog.home.notFound">No Blogs found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Blog;
