import React from 'react';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from "@material-ui/core/Toolbar";
import IconButton from "@material-ui/core/IconButton";
import Typography from "@material-ui/core/Typography";
import MenuIcon from '@material-ui/icons/Menu';
import Button from "@material-ui/core/Button";
import makeStyles from "@material-ui/core/styles/makeStyles";
import LoginModal from "./components/LoginModal";
import {useDispatch, useSelector} from "react-redux";
import {changeRoleAction, closeAuthModalAction, logoutAction, openAuthModalAction} from "./actions/auth";
import Container from "@material-ui/core/Container";
import AuthTest from "./components/AuthTest";
import Select from "@material-ui/core/Select";
import MenuItem from "@material-ui/core/MenuItem";
import {translations} from "./constants";


const useStyles = makeStyles(theme => ({
    title: {
        flexGrow: 1
    },
    select: {
        minWidth: 120,
    }
}));

function App() {
    const classes = useStyles();

    const loggedIn = useSelector(state => state.auth.loggedIn);
    const open = useSelector(state => state.auth.openModal);
    const user = useSelector(state => state.auth.user);

    const dispatch = useDispatch();
    const handleOpen = () => {
        dispatch(openAuthModalAction());
    };

    const handleClose = () => {
        dispatch(closeAuthModalAction());
    };

    const logout = () => {
        dispatch(logoutAction());
    };

    const handleRoleChange = (event) => {
        dispatch(changeRoleAction(event.target.value));
    };

    return (
        <div className="App">
            <AppBar position="static">
                <Toolbar>
                    <IconButton edge="start" color="inherit" aria-label="menu">
                        <MenuIcon/>
                    </IconButton>
                    <Typography variant="h6" className={classes.title}>
                        Bilelotpol
                    </Typography>
                    {
                        loggedIn && user.authorities.length > 1 &&
                        <Select
                            className={classes.select}
                            value={user.role}
                            onChange={handleRoleChange}
                            label="Rola"
                        >
                            {user.authorities.map(authority => <MenuItem key={authority}
                                value={authority}>{translations.roles[authority]}</MenuItem>)}
                        </Select>
                    }
                    {!loggedIn && <Button color="inherit" onClick={handleOpen}>Zaloguj się</Button>}
                    {loggedIn && <Button color="inherit" onClick={logout}>Wyloguj się</Button>}
                </Toolbar>
            </AppBar>
            <Container>
                {loggedIn && <>
                    <Typography variant="h2">Dzień dobry, {user.principal}!</Typography>
                    <Button variant="contained" color="secondary">{`Tylko dla ${translations.roles[user.role]}`}</Button>
                </>}
                <AuthTest/>
            </Container>
            <LoginModal open={open} handleClose={handleClose}/>
        </div>
    );
}

export default App;
