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

describe('FormAnswer e2e test', () => {
  const formAnswerPageUrl = '/form-answer';
  const formAnswerPageUrlPattern = new RegExp('/form-answer(\\?.*)?$');
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
    cy.intercept('GET', '/api/form-answers+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/form-answers').as('postEntityRequest');
    cy.intercept('DELETE', '/api/form-answers/*').as('deleteEntityRequest');
  });

  it('should load FormAnswers', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('form-answer');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('FormAnswer').should('exist');
    cy.url().should('match', formAnswerPageUrlPattern);
  });

  it('should load details FormAnswer page', function () {
    cy.visit(formAnswerPageUrl);
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        this.skip();
      }
    });
    cy.get(entityDetailsButtonSelector).first().click({ force: true });
    cy.getEntityDetailsHeading('formAnswer');
    cy.get(entityDetailsBackButtonSelector).click({ force: true });
    cy.wait('@entitiesRequest').then(({ response }) => {
      expect(response.statusCode).to.equal(200);
    });
    cy.url().should('match', formAnswerPageUrlPattern);
  });

  it('should load create FormAnswer page', () => {
    cy.visit(formAnswerPageUrl);
    cy.wait('@entitiesRequest');
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateUpdateHeading('FormAnswer');
    cy.get(entityCreateSaveButtonSelector).should('exist');
    cy.get(entityCreateCancelButtonSelector).click({ force: true });
    cy.wait('@entitiesRequest').then(({ response }) => {
      expect(response.statusCode).to.equal(200);
    });
    cy.url().should('match', formAnswerPageUrlPattern);
  });

  it('should load edit FormAnswer page', function () {
    cy.visit(formAnswerPageUrl);
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        this.skip();
      }
    });
    cy.get(entityEditButtonSelector).first().click({ force: true });
    cy.getEntityCreateUpdateHeading('FormAnswer');
    cy.get(entityCreateSaveButtonSelector).should('exist');
    cy.get(entityCreateCancelButtonSelector).click({ force: true });
    cy.wait('@entitiesRequest').then(({ response }) => {
      expect(response.statusCode).to.equal(200);
    });
    cy.url().should('match', formAnswerPageUrlPattern);
  });

  it('should create an instance of FormAnswer', () => {
    cy.visit(formAnswerPageUrl);
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateUpdateHeading('FormAnswer');

    cy.get(`[data-cy="creationForm"]`).type('2021-07-09T01:10').should('have.value', '2021-07-09T01:10');

    cy.get(`[data-cy="modifiedForm"]`).type('2021-07-08T13:02').should('have.value', '2021-07-08T13:02');

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
    cy.url().should('match', formAnswerPageUrlPattern);
  });

  it('should delete last instance of FormAnswer', function () {
    cy.intercept('GET', '/api/form-answers/*').as('dialogDeleteRequest');
    cy.visit(formAnswerPageUrl);
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length > 0) {
        cy.get(entityTableSelector).should('have.lengthOf', response.body.length);
        cy.get(entityDeleteButtonSelector).last().click({ force: true });
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('formAnswer').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click({ force: true });
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', formAnswerPageUrlPattern);
      } else {
        this.skip();
      }
    });
  });
});
