import { refreshTokenAction } from './actions/auth';
import store from './store';

const minute = 60 * 1000;
const tokenValidity = minute * process.env.REACT_APP_JWT_VALIDITY;

const worker = () => {
    setInterval(() => {
        if (
            store.getState().auth.loggedIn &&
            store.getState().auth.tokenExp < Date.now() / 1000 + tokenValidity / 2 / 1000
        ) {
            store.dispatch(refreshTokenAction());
        }
    }, tokenValidity / 4);
};

export default function refreshToken() {
    if (store.getState().auth.loggedIn) store.dispatch(refreshTokenAction());
    worker();
}
