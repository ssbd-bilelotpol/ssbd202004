import React from 'react';
import Button from "@material-ui/core/Button";
import {makeStyles} from "@material-ui/core/styles";
import {Formik, Form, Field} from 'formik';
import {TextField} from 'formik-material-ui';
import LinearProgress from "@material-ui/core/LinearProgress";
import * as Yup from 'yup';
import {connect} from "react-redux";
import {login} from "../actions/auth";

const LoginSchema = Yup.object().shape({
   login: Yup.string()
       .required('Required'),
    password: Yup.string()
        .required('Required')
});

const useStyles = makeStyles((theme) => ({
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

const LoginForm = ({dispatch}) => {
    const classes = useStyles();
    return (
        <Formik
            initialValues={{
                login: '',
                password: '',
            }}
            validationSchema={LoginSchema}
            onSubmit={(values, {setSubmitting}) => {
                dispatch(login(values.login, values.password))
                    .then(() => setSubmitting(false));
            }}
        >
            {({submitForm, isSubmitting}) => (
                <Form className={classes.form}>
                    <div>
                        <Field
                            component={TextField}
                            name="login"
                            label="Login"
                        />
                    </div>
                    <div>
                        <Field
                            component={TextField}
                            type="password"
                            label="Password"
                            name="password"
                        />
                        {isSubmitting && <LinearProgress/>}
                    </div>
                    <div className={classes.login}>
                        <Button
                            variant="contained"
                            color="primary"
                            disabled={isSubmitting}
                            onClick={submitForm}
                        >
                            Log In
                        </Button>
                    </div>
                </Form>
            )}
        </Formik>
    );
};

export default connect()(LoginForm);