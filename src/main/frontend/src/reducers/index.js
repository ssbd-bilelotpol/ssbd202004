import { combineReducers } from 'redux';
import auth from './auth';
import accounts from './accounts';
import accountsAuthReport from './accountsAuthReport';

export default combineReducers({
    auth,
    accounts,
    accountsAuthReport,
});
