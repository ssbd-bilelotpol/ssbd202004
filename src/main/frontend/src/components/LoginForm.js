import React from 'react';
import Button from "@material-ui/core/Button";
import {makeStyles} from "@material-ui/core/styles";
import {Formik, Form, Field} from 'formik';
import {TextField} from 'formik-material-ui';
import LinearProgress from "@material-ui/core/LinearProgress";
import * as Yup from 'yup';
import {connect, useSelector} from "react-redux";
import {loginAction} from "../actions/auth";
import FormHelperText from "@material-ui/core/FormHelperText";

const LoginSchema = Yup.object().shape({
    username: Yup.string()
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
    const error = useSelector(state => state.auth.error);
    return (
        <Formik
            initialValues={{
                username: '',
                password: '',
            }}
            validationSchema={LoginSchema}
            onSubmit={(values, {setSubmitting}) => {
                dispatch(loginAction(values.username, values.password))
                    .then(() => setSubmitting(false));
            }}
        >
            {({submitForm, isSubmitting}) => (
                <Form className={classes.form}>
                    <div>
                        <Field
                            component={TextField}
                            name="username"
                            label="Nazwa użytkownika"
                        />
                    </div>
                    <div>
                        <Field
                            component={TextField}
                            type="password"
                            label="Hasło"
                            name="password"
                        />
                        {isSubmitting && <LinearProgress/>}
                    </div>
                    {error && <FormHelperText error={true}>{error}</FormHelperText>}
                    <div className={classes.login}>
                        <Button
                            variant="contained"
                            color="primary"
                            disabled={isSubmitting}
                            onClick={submitForm}
                        >
                            Zaloguj się
                        </Button>
                    </div>

                </Form>
            )}
        </Formik>
    );
};

export default connect()(LoginForm);