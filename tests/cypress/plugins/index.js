/**
 * @type {Cypress.PluginConfig}
 */
module.exports = (on, config) => {
    on('before:browser:launch', (browser, launchOptions) => {
        if (browser.family === 'chromium' && browser.name !== 'electron') {
            launchOptions.preferences.default.intl = {accept_languages: "en"};
            return launchOptions
        }
    });
};
