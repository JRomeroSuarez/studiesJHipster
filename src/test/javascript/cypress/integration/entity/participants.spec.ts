import { entityItemSelector } from '../../support/commands';
import {
  entityTableSelector,
  entityDetailsButtonSelector,
  entityDetailsBackButtonSelector,
  entityCreateButtonSelector,
  entityCreateSaveButtonSelector,
  entityCreateCancelButtonSelector,
  entityEditButtonSelector,
  entityDeleteButtonSelector,
  entityConfirmDeleteButtonSelector,
} from '../../support/entity';

describe('Participants e2e test', () => {
  const participantsPageUrl = '/participants';
  const participantsPageUrlPattern = new RegExp('/participants(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'admin';
  const password = Cypress.env('E2E_PASSWORD') ?? 'admin';

  before(() => {
    cy.window().then(win => {
      win.sessionStorage.clear();
    });
    cy.visit('');
    cy.login(username, password);
    cy.get(entityItemSelector).should('exist');
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/participants+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/participants').as('postEntityRequest');
    cy.intercept('DELETE', '/api/participants/*').as('deleteEntityRequest');
  });

  it('should load Participants', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('participants');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Participants').should('exist');
    cy.url().should('match', participantsPageUrlPattern);
  });

  it('should load details Participants page', function () {
    cy.visit(participantsPageUrl);
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        this.skip();
      }
    });
    cy.get(entityDetailsButtonSelector).first().click({ force: true });
    cy.getEntityDetailsHeading('participants');
    cy.get(entityDetailsBackButtonSelector).click({ force: true });
    cy.wait('@entitiesRequest').then(({ response }) => {
      expect(response.statusCode).to.equal(200);
    });
    cy.url().should('match', participantsPageUrlPattern);
  });

  it('should load create Participants page', () => {
    cy.visit(participantsPageUrl);
    cy.wait('@entitiesRequest');
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateUpdateHeading('Participants');
    cy.get(entityCreateSaveButtonSelector).should('exist');
    cy.get(entityCreateCancelButtonSelector).click({ force: true });
    cy.wait('@entitiesRequest').then(({ response }) => {
      expect(response.statusCode).to.equal(200);
    });
    cy.url().should('match', participantsPageUrlPattern);
  });

  it('should load edit Participants page', function () {
    cy.visit(participantsPageUrl);
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        this.skip();
      }
    });
    cy.get(entityEditButtonSelector).first().click({ force: true });
    cy.getEntityCreateUpdateHeading('Participants');
    cy.get(entityCreateSaveButtonSelector).should('exist');
    cy.get(entityCreateCancelButtonSelector).click({ force: true });
    cy.wait('@entitiesRequest').then(({ response }) => {
      expect(response.statusCode).to.equal(200);
    });
    cy.url().should('match', participantsPageUrlPattern);
  });

  it('should create an instance of Participants', () => {
    cy.visit(participantsPageUrl);
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateUpdateHeading('Participants');

    cy.get(`[data-cy="email"]`).type('Ray57@yahoo.com').should('have.value', 'Ray57@yahoo.com');

    cy.get(`[data-cy="associatedForms"]`).type('Home Minor').should('have.value', 'Home Minor');

    cy.get(`[data-cy="formsCompleted"]`).type('858').should('have.value', '858');

    cy.get(`[data-cy="languaje"]`).type('Small').should('have.value', 'Small');

    cy.get(`[data-cy="invitationStatus"]`).select('PENDING');

    cy.get(`[data-cy="actions"]`).type('Human auxiliary').should('have.value', 'Human auxiliary');

    cy.setFieldSelectToLastOfEntity('study');

    cy.get(entityCreateSaveButtonSelector).click({ force: true });
    cy.scrollTo('top', { ensureScrollable: false });
    cy.get(entityCreateSaveButtonSelector).should('not.exist');
    cy.wait('@postEntityRequest').then(({ response }) => {
      expect(response.statusCode).to.equal(201);
    });
    cy.wait('@entitiesRequest').then(({ response }) => {
      expect(response.statusCode).to.equal(200);
    });
    cy.url().should('match', participantsPageUrlPattern);
  });

  it('should delete last instance of Participants', function () {
    cy.intercept('GET', '/api/participants/*').as('dialogDeleteRequest');
    cy.visit(participantsPageUrl);
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length > 0) {
        cy.get(entityTableSelector).should('have.lengthOf', response.body.length);
        cy.get(entityDeleteButtonSelector).last().click({ force: true });
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('participants').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click({ force: true });
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', participantsPageUrlPattern);
      } else {
        this.skip();
      }
    });
  });
});
