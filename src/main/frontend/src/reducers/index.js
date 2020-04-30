import { combineReducers } from 'redux';
import auth from './auth';
import accounts from './accounts';

export default combineReducers({
    auth,
    accounts,
});
