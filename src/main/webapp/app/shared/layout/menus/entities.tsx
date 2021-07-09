import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { Translate, translate } from 'react-jhipster';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown
    icon="th-list"
    name={translate('global.menu.entities.main')}
    id="entity-menu"
    data-cy="entity"
    style={{ maxHeight: '80vh', overflow: 'auto' }}
  >
    <>{/* to avoid warnings when empty */}</>
    <MenuItem icon="asterisk" to="/study">
      <Translate contentKey="global.menu.entities.study" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/participants">
      <Translate contentKey="global.menu.entities.participants" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/forms">
      <Translate contentKey="global.menu.entities.forms" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/questions">
      <Translate contentKey="global.menu.entities.questions" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/answer">
      <Translate contentKey="global.menu.entities.answer" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/form-answer">
      <Translate contentKey="global.menu.entities.formAnswer" />
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
