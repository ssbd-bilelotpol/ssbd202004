const login = Cypress.env('login');

const changeAccessLevel = (accessLevel) => {
    cy.get(`#${accessLevel}[type="checkbox"]`).click({force: true});
    cy.get('#saveAccessLevelsButton').click()
    cy.get('.ui.success.message').should('be.visible')

};

describe('change access level', () => {
    it('change logged user access level', () => {
        cy.visit('/admin/accounts/list');
        cy.get(`.button[href="/admin/accounts/${login}/edit"]`)
            .click()
            .then(() => {
                changeAccessLevel('client');
                cy.get('#client[type="checkbox"]').should('not.be.checked');
                changeAccessLevel('client');
                cy.get('#client[type="checkbox"]').should('be.checked');
            });
        cy.contains('admin').click().then(() => {
            cy.contains('client').should('be.visible');
        });
    })
});