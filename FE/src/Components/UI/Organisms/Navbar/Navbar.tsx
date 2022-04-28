import React, { useEffect, useState } from 'react';
import AppBar from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import { useLocation, Link } from 'react-router-dom';
import axios from 'axios';

const transitionStyle = {
  transitionDuration: '0.2s',
  transitionProperty: 'all',
};

export default function Navbar() {
  const [pageName, setPageName] = useState('');
  const location = useLocation();
  const [open, setOpen] = useState(false);

  const loginInfo = window.localStorage.getItem('login');
  const logout = () => {
    window.localStorage.removeItem('login');
    axios
      .post(`/api/user/auth/signout`)
      .then()
      .catch((err) => console.log(err));
  };

  const handleScroll = () => {
    if (window.scrollY) setOpen(true);
    else setOpen(false);
  };

  document.addEventListener('scroll', handleScroll);

  useEffect(() => {
    const name = location.pathname.split('/');
    if (name.length !== 2 || name[1] === '') {
      setPageName('Main');
    } else {
      setPageName(name[1].replace(/\b[a-z]/, (letter) => letter.toUpperCase()));
    }

    return () => {
      setPageName('');
    };
  }, [location]);

  return (
    <AppBar
      position="sticky"
      sx={{
        top: 24,
        borderRadius: 3,
        backgroundColor: 'rgba(230,230,230,0.5)',
        color: 'black',
      }}
      style={
        open
          ? {}
          : {
              boxShadow: 'none',
            }
      }
    >
      <Toolbar>
        <Typography variant="h3" component="div" sx={{ flexGrow: 1 }}>
          {pageName}
        </Typography>
        {loginInfo ? (
          <Link to="/login" style={{ color: 'black', textDecoration: 'none' }}>
            <Button color="inherit" onClick={logout}>
              Logout
            </Button>
          </Link>
        ) : (
          <Link to="/login" style={{ color: 'black', textDecoration: 'none' }}>
            <Button color="inherit">Login</Button>
          </Link>
        )}
      </Toolbar>
    </AppBar>
  );
}
