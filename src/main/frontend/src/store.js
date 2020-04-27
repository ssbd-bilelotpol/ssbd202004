import { createStore, applyMiddleware, compose } from 'redux';
import jwtDecode from 'jwt-decode';
import thunk from 'redux-thunk';
import rootReducer from './reducers';

const composeEnhancers = window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ || compose;

let user = localStorage.getItem('user') ? JSON.parse(localStorage.getItem('user')) : {};
if (user.token) {
    const token = jwtDecode(user.token);
    if (token.exp < Date.now() / 1000) {
        user = {};
    }
}

const initialState = {
    auth: {
        user,
        loggedIn: !!user.principal,
    },
};

const store = createStore(rootReducer, initialState, composeEnhancers(applyMiddleware(thunk)));

store.subscribe(() => {
    if (store.getState().auth.user !== null) {
        localStorage.setItem('user', JSON.stringify(store.getState().auth.user));
    }
});

export default store;
