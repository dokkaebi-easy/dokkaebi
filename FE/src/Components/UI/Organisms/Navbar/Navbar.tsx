import React, { useContext, useEffect, useState } from 'react';
import AppBar from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import { useLocation, Link } from 'react-router-dom';
import axios from 'axios';
import IconButton from '@mui/material/IconButton';
import Brightness4Icon from '@mui/icons-material/Brightness4';
import Brightness7Icon from '@mui/icons-material/Brightness7';
import { ColorModeContext } from 'App';
import { useTheme } from '@mui/material/styles';

export default function Navbar() {
  const theme = useTheme();
  const colorMode = React.useContext(ColorModeContext);

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
        <Link to="/">
          <img src="/assets/dockerby.svg" width="80" height="80" alt="" />
        </Link>
        <Typography variant="h3" component="div" sx={{ flexGrow: 1 }}>
          {pageName}
        </Typography>
        <IconButton
          sx={{ ml: 1 }}
          color="inherit"
          onClick={colorMode.toggleColorMode}
        >
          {theme.palette.mode === 'dark' ? (
            <Brightness7Icon />
          ) : (
            <Brightness4Icon />
          )}
        </IconButton>
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
