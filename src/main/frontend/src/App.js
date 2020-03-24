import React from 'react';
import AppBar from '@material-ui/core/AppBar';
import './App.css';
import Toolbar from "@material-ui/core/Toolbar";
import IconButton from "@material-ui/core/IconButton";
import Typography from "@material-ui/core/Typography";
import MenuIcon from '@material-ui/icons/Menu';
import Button from "@material-ui/core/Button";
import makeStyles from "@material-ui/core/styles/makeStyles";
import LoginModal from "./components/LoginModal";
import {useDispatch, useSelector} from "react-redux";
import {closeAuthModalAction, logoutAction, openAuthModalAction} from "./actions/auth";
import Container from "@material-ui/core/Container";


const useStyles = makeStyles(theme => ({
    title: {
        flexGrow: 1
    },
}));

function App() {
    const classes = useStyles();

    const loggedIn = useSelector(state => state.auth.loggedIn);
    const open = useSelector(state => state.auth.openModal);
    const principal = useSelector(state => state.auth.principal);
    const authorities = useSelector(state => state.auth.authorities);
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
                    {!loggedIn && <Button color="inherit" onClick={handleOpen}>Login</Button>}
                    {loggedIn && <Button color="inherit" onClick={logout}>Logout</Button>}
                </Toolbar>
            </AppBar>
            {loggedIn && <Container>
                <h2>Welcome {principal}!</h2>
                <ul>
                    {authorities.map(auth => <li key={auth}>{auth}</li>)}
                </ul>
            </Container>
            }
            <LoginModal open={open} handleClose={handleClose}/>
        </div>
    );
}

export default App;
