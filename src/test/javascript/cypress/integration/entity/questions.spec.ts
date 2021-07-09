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

describe('Questions e2e test', () => {
  const questionsPageUrl = '/questions';
  const questionsPageUrlPattern = new RegExp('/questions(\\?.*)?$');
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
    cy.intercept('GET', '/api/questions+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/questions').as('postEntityRequest');
    cy.intercept('DELETE', '/api/questions/*').as('deleteEntityRequest');
  });

  it('should load Questions', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('questions');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Questions').should('exist');
    cy.url().should('match', questionsPageUrlPattern);
  });

  it('should load details Questions page', function () {
    cy.visit(questionsPageUrl);
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        this.skip();
      }
    });
    cy.get(entityDetailsButtonSelector).first().click({ force: true });
    cy.getEntityDetailsHeading('questions');
    cy.get(entityDetailsBackButtonSelector).click({ force: true });
    cy.wait('@entitiesRequest').then(({ response }) => {
      expect(response.statusCode).to.equal(200);
    });
    cy.url().should('match', questionsPageUrlPattern);
  });

  it('should load create Questions page', () => {
    cy.visit(questionsPageUrl);
    cy.wait('@entitiesRequest');
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateUpdateHeading('Questions');
    cy.get(entityCreateSaveButtonSelector).should('exist');
    cy.get(entityCreateCancelButtonSelector).click({ force: true });
    cy.wait('@entitiesRequest').then(({ response }) => {
      expect(response.statusCode).to.equal(200);
    });
    cy.url().should('match', questionsPageUrlPattern);
  });

  it('should load edit Questions page', function () {
    cy.visit(questionsPageUrl);
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        this.skip();
      }
    });
    cy.get(entityEditButtonSelector).first().click({ force: true });
    cy.getEntityCreateUpdateHeading('Questions');
    cy.get(entityCreateSaveButtonSelector).should('exist');
    cy.get(entityCreateCancelButtonSelector).click({ force: true });
    cy.wait('@entitiesRequest').then(({ response }) => {
      expect(response.statusCode).to.equal(200);
    });
    cy.url().should('match', questionsPageUrlPattern);
  });

  it('should create an instance of Questions', () => {
    cy.visit(questionsPageUrl);
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateUpdateHeading('Questions');

    cy.get(`[data-cy="subtitle"]`).type('mint Car Island').should('have.value', 'mint Car Island');

    cy.get(`[data-cy="info"]`).type('monitoring fuchsia').should('have.value', 'monitoring fuchsia');

    cy.get(`[data-cy="fieldType"]`).select('FREE_TEXT');

    cy.get(`[data-cy="mandatory"]`).should('not.be.checked');
    cy.get(`[data-cy="mandatory"]`).click().should('be.checked');

    cy.get(`[data-cy="variableName"]`).type('Berkshire firewall Berkshire').should('have.value', 'Berkshire firewall Berkshire');

    cy.get(`[data-cy="units"]`).type('seamless').should('have.value', 'seamless');

    cy.get(`[data-cy="conditional"]`).should('not.be.checked');
    cy.get(`[data-cy="conditional"]`).click().should('be.checked');

    cy.get(`[data-cy="creationDate"]`).type('2021-07-08T20:34').should('have.value', '2021-07-08T20:34');

    cy.get(`[data-cy="editDate"]`).type('2021-07-08T14:08').should('have.value', '2021-07-08T14:08');

    cy.get(`[data-cy="actions"]`).type('Sao Canyon infomediaries').should('have.value', 'Sao Canyon infomediaries');

    cy.setFieldSelectToLastOfEntity('forms');

    cy.get(entityCreateSaveButtonSelector).click({ force: true });
    cy.scrollTo('top', { ensureScrollable: false });
    cy.get(entityCreateSaveButtonSelector).should('not.exist');
    cy.wait('@postEntityRequest').then(({ response }) => {
      expect(response.statusCode).to.equal(201);
    });
    cy.wait('@entitiesRequest').then(({ response }) => {
      expect(response.statusCode).to.equal(200);
    });
    cy.url().should('match', questionsPageUrlPattern);
  });

  it('should delete last instance of Questions', function () {
    cy.intercept('GET', '/api/questions/*').as('dialogDeleteRequest');
    cy.visit(questionsPageUrl);
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length > 0) {
        cy.get(entityTableSelector).should('have.lengthOf', response.body.length);
        cy.get(entityDeleteButtonSelector).last().click({ force: true });
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('questions').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click({ force: true });
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', questionsPageUrlPattern);
      } else {
        this.skip();
      }
    });
  });
});
