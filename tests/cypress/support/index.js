let user;
const backend = Cypress.env('backend');
const login = Cypress.env('login');
const password = Cypress.env('password');

before(function fetchUser() {
    cy.request('POST', `${backend}/auth`, {
        username: login,
        password: password,
    })
        .its('body')
        .then((res) => {
            user = res;
        })
});

before(function setLanguage() {
    Object.defineProperty(navigator, 'language', {value: 'en-US'});
});

beforeEach(function setUser() {
    cy.visit('/', {
        onBeforeLoad(win) {
            win.localStorage.setItem('user', JSON.stringify({
                ...user,
                roles: user.authorities,
                role: "admin",
            }));
        },
    });
});
