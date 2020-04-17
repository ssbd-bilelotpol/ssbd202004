import React from 'react';
import ReactDOM from 'react-dom';
import {createStore, applyMiddleware, compose} from 'redux';
import {Provider} from 'react-redux';
import thunk from 'redux-thunk';
import { createMuiTheme } from '@material-ui/core/styles';

import rootReducer from './reducers';
import './index.css';
import App from './App';
import * as serviceWorker from './serviceWorker';
import {ThemeProvider} from "@material-ui/styles";
import jwtDecode from "jwt-decode";

const composeEnhancers = window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ || compose;

let user = localStorage.getItem('user') ? JSON.parse(localStorage.getItem('user')) : null;
if (user && user.token) {
    const token = jwtDecode(user.token);
    if (token.exp < Date.now() / 1000) {
        user = null;
    }
}

const initialState = {
    auth: {
        user,
        loggedIn: !!user.principal
    }
};

const store = createStore(rootReducer, initialState, composeEnhancers(
  applyMiddleware(thunk)
));

export function getStore() {
    return store;
}

store.subscribe(() => {
   if (store.getState().auth.user !== null) {
       localStorage.setItem('user', JSON.stringify(store.getState().auth.user));
   }
});

const theme = createMuiTheme({
    palette: {
        primary: {
            light: '#9be7ff',
            main: '#64b5f6',
            dark: '#2286c3',
            contrastText: '#000',
        },
        secondary: {
            light: '#ffd149',
            main: '#ffa000',
            dark: '#c67100',
            contrastText: '#000',
        },
    },
});

const Wrapper = () => (
    <Provider store={store}>
        <ThemeProvider theme={theme}>
            <App/>
        </ThemeProvider>
    </Provider>
);

ReactDOM.render(<Wrapper/>, document.getElementById('root'));

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
