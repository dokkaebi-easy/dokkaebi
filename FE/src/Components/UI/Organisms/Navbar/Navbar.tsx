import React, { useEffect, useState } from 'react';
import AppBar from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import { useLocation, Link } from 'react-router-dom';
import axios from 'axios';
import { styled, useTheme } from '@mui/material/styles';

export default function Navbar() {
  const theme = useTheme();

  const [pageName, setPageName] = useState('');
  const [open, setOpen] = useState(false);

  const location = useLocation();
  const loginInfo = sessionStorage.getItem('login');

  const logout = () => {
    sessionStorage.removeItem('login');
    axios.post(`/api/user/auth/signout`).then().catch();
  };

  const handleScroll = () => {
    if (window.scrollY) setOpen(true);
    else setOpen(false);
  };

  document.addEventListener('scroll', handleScroll);

  useEffect(() => {
    const name = location.pathname.split('/');
    if (name.length < 2 || name[1] === '') {
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
      position="relative"
      sx={{
        borderRadius: 3,
        background:
          theme.palette.mode === 'dark'
            ? 'rgba(21,21,21,0.5)'
            : 'rgba(240,240,240,0.5)',
        color: theme.palette.mode === 'dark' ? 'white' : 'black',
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
        <Link to="/">
          <img src="/assets/dockerby_1.png" width="80" height="80" alt="" />
        </Link>
        <Typography variant="h3" component="div" sx={{ flexGrow: 1 }}>
          {pageName}
        </Typography>

        {loginInfo ? (
          <Link
            to="/login"
            style={{
              color: theme.palette.mode === 'dark' ? 'white' : 'black',
              textDecoration: 'none',
            }}
          >
            <Button color="inherit" onClick={logout}>
              Logout
            </Button>
          </Link>
        ) : (
          <Link
            to="/login"
            style={{
              color: theme.palette.mode === 'dark' ? 'white' : 'black',
              textDecoration: 'none',
            }}
          >
            <Button color="inherit">Login</Button>
          </Link>
        )}
      </Toolbar>
    </AppBar>
  );
}
