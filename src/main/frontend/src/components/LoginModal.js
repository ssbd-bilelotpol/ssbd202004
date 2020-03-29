import React from 'react';
import Modal from "@material-ui/core/Modal";
import Backdrop from "@material-ui/core/Backdrop";
import Fade from "@material-ui/core/Fade";
import {makeStyles} from "@material-ui/core/styles";
import LoginForm from "./LoginForm";

const useStyles = makeStyles((theme) => ({
        modal: {
            display: 'flex',
            justifyContent: 'center',
            alignItems: 'center'
        },
        paper: {
            '&:focus': {
                outline: 'none'
            },
            backgroundColor: theme.palette.background.paper,
            padding: theme.spacing(2, 4, 2),
        },
        form: {
            '& > *': {
                margin: theme.spacing(1),
            },
        },
        login: {
            marginTop: theme.spacing(2),
            display: 'flex',
            justifyContent: 'center'
        }
    }
));

const LoginModal = ({open, handleClose}) => {
    const classes = useStyles();

    return <Modal
        aria-labelledby="login-modal-title"
        aria-describedby="login-modal-description"
        className={classes.modal}
        open={open}
        onClose={handleClose}
        closeAfterTransition
        BackdropComponent={Backdrop}
        BackdropProps={{
            timeout: 500,
        }}
    >
        <Fade in={open}>
            <div className={classes.paper}>
                <h2 id="login-modal-title">Logowanie</h2>
                <p id="login-modal-description">Wprowadź dane logowania</p>
                <LoginForm/>
            </div>
        </Fade>
    </Modal>
};

export default LoginModal;