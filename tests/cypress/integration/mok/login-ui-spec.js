const backend = Cypress.env('backend');
const login = Cypress.env('login');
const password = Cypress.env('password');

describe('login', () => {
    it('login using UI', () => {
        cy.visit('/', {
            onBeforeLoad(win) {
                win.localStorage.clear();
            }
        });
        cy.get('#loginButton').click();
        cy.get('[name=login]').type(login);
        cy.get('[name=password]').type(password);
        cy.get('#loginFormButton').click();

        cy.location('pathname').should('equal', '/admin');
        cy.contains('admin')
            .should('be.visible')
            .then(() => {
                const userString = window.localStorage.getItem('user');

                expect(userString).to.be.a('string');
                const user = JSON.parse(userString);

                expect(user).to.be.an('object');
                expect(user).to.have.keys([
                    'principal',
                    'role',
                    'roles',
                    'token',
                    'tokenExp',
                ]);
                expect(user.token).to.be.a('string')
            });
        cy.contains(login).click().then(() => {
            cy.get('#logoutButton').click();
            cy.location('pathname').should('equal', '/');
        })
    });
    it('fails to request a protected resource', () => {
        cy.request({
            url: `${backend}/accounts`,
            failOnStatusCode: false,
        })
            .its('status')
            .should('equal', 404)
    });
});