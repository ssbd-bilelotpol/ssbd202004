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
import {useSelector} from "react-redux";


const useStyles = makeStyles(theme => ({
  title: {
    flexGrow: 1
  },
}));

function App() {
  const classes = useStyles();
  const [open, setOpen] = React.useState(false);

  const loggedIn = useSelector(state => state.auth.loggedIn);
  const handleOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  return (
    <div className="App">
      <AppBar position="static">
        <Toolbar>
          <IconButton edge="start" color="inherit" aria-label="menu">
            <MenuIcon />
          </IconButton>
          <Typography variant="h6" className={classes.title}>
            Bilelotpol
          </Typography>
          {!loggedIn && <Button color="inherit" onClick={handleOpen}>Login</Button>}
        </Toolbar>
      </AppBar>
      <LoginModal open={open && !loggedIn} handleClose={handleClose}/>
    </div>
  );
}

export default App;
