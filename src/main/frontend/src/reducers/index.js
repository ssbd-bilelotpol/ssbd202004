import { combineReducers } from 'redux';
import auth from './auth';
import accounts from './accounts';
import accountsAuthReport from './accountsAuthReport';
import breadcrumbs from './breadcrumbs';

export default combineReducers({
    auth,
    accounts,
    accountsAuthReport,
    breadcrumbs,
});
