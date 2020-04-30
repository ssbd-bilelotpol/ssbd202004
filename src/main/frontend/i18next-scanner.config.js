module.exports = {
    input: ['src/**/*.{js,jsx}', 'src/*.{js,jsx}', '!i18n/**', '!**/node_modules/**'],
    output: './',
    options: {
        debug: true,
        func: {
            list: ['t'],
            extensions: ['.js', '.jsx'],
        },
        trans: {
            component: 'Trans',
            i18nKey: 'i18nKey',
            defaultsKey: 'defaults',
            extensions: ['.js', '.jsx'],
            acorn: {
                ecmaVersion: 10,
                sourceType: 'module',
            },
        },
        lngs: ['pl'],
        ns: ['resource'],
        defaultLng: 'en',
        defaultNs: 'resource',
        defaultValue: '__STRING_NOT_TRANSLATED__',
        resource: {
            loadPath: 'src/i18n/{{lng}}/{{ns}}.json',
            savePath: 'src/i18n/{{lng}}/{{ns}}.json',
            jsonIndent: 4,
            lineEnding: '\n',
        },
        nsSeparator: false,
        keySeparator: false,
        interpolation: {
            prefix: '{{',
            suffix: '}}',
        },
    },
};
