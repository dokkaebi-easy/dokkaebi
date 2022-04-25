import React, { useEffect, useState } from 'react';
import AppBar from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import { useLocation, Link } from 'react-router-dom';

export default function Navbar() {
  const [pageName, setPageName] = useState('');
  const location = useLocation();

  useEffect(() => {
    const name = location.pathname.split('/');
    if (name.length !== 2 || name[1] === '') {
      setPageName('Main');
    } else {
      setPageName(name[1].replace(/\b[a-z]/, (letter) => letter.toUpperCase()));
    }
  }, [location]);
  return (
    <AppBar
      position="sticky"
      sx={{
        top: 24,
        borderRadius: 3,
        backgroundColor: 'rgba(200,200,200,0.5)',
      }}
    >
      <Toolbar>
        <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
          {pageName}
        </Typography>
        <Link to="/login">
          <Button color="inherit">Login</Button>
        </Link>
      </Toolbar>
    </AppBar>
  );
}
