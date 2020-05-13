/* eslint-disable react/jsx-filename-extension */
import React from 'react';
import ReactDOM from 'react-dom';
import { Provider } from 'react-redux';
import { BrowserRouter as Router } from 'react-router-dom';
import 'semantic-ui-css/semantic.min.css';
import './i18n/i18n';
import './index.css';
import App from './App';
import store from './store';

const Wrapper = () => (
    <Provider store={store}>
        <Router basename={process.env.PUBLIC_URL}>
            <App />
        </Router>
    </Provider>
);

ReactDOM.render(<Wrapper />, document.getElementById('root'));

export default function getStore() {
    return store;
}
