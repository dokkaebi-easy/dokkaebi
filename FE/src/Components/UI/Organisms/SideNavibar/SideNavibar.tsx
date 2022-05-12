import Paper from '@mui/material/Paper';
import Box from '@mui/material/Box';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import Divider from '@mui/material/Divider';
import DashboardIcon from '@mui/icons-material/Dashboard';
import SettingsIcon from '@mui/icons-material/Settings';
import { useLocation, Link } from 'react-router-dom';
import { useEffect, useState } from 'react';

const linestyle = {
  height: '1px',
  backgroundImage:
    'linear-gradient(to right, rgba(255, 255, 255, 0), #ffffff, rgba(255, 255, 255, 0))',
};
export default function SideNavibar() {
  const [pageName, setPageName] = useState('');
  const location = useLocation();

  useEffect(() => {
    const name = location.pathname.split('/');
    setPageName(name[1]);
  }, [location]);

  return (
    <Paper
      sx={{
        position: 'fixed',
        top: 24,
        left: 24,
        height: '95vh',
        width: 250,
        borderRadius: 5,
        background: 'linear-gradient(195deg, #42424a, #191919)',
      }}
      elevation={3}
    >
      <Box sx={{ color: 'white', textAlign: 'center' }}>
        <List>
          <ListItem disablePadding alignItems="center">
            <Link
              to="/"
              style={{
                color: 'white',
                textDecoration: 'none',
                width: '90%',
                margin: 'auto',
              }}
            >
              <ListItemButton>
                <ListItemIcon>
                  <img
                    src="/assets/dockerby.svg"
                    width="40"
                    height="40"
                    alt=""
                  />
                </ListItemIcon>
                <ListItemText primary="Dockerby" />
              </ListItemButton>
            </Link>
          </ListItem>
        </List>
        <Divider light sx={linestyle} />
        <List>
          <ListItem disablePadding>
            <Link
              to="/setting/0"
              style={{
                color: 'white',
                textDecoration: 'none',
                width: '90%',
                margin: '0 auto',
                marginTop: 3,
                background: 'linear-gradient(45deg, #757F9A, #D7DDE8)',
                borderRadius: 5,
              }}
            >
              <ListItemButton>
                <ListItemIcon>
                  <DashboardIcon sx={{ color: 'white' }} />
                </ListItemIcon>
                <ListItemText primary="Build Create" />
              </ListItemButton>
            </Link>
          </ListItem>
          {pageName === 'detail' ? (
            <ListItem
              disablePadding
              style={{
                color: 'white',
                textDecoration: 'none',
                width: '90%',
                margin: '0 auto',
                marginTop: 3,
                background: 'linear-gradient(45deg, #757F9A, #D7DDE8)',
                borderRadius: 5,
              }}
            >
              <ListItemButton>
                <ListItemIcon>
                  <SettingsIcon sx={{ color: 'white' }} />
                </ListItemIcon>
                <ListItemText primary="Edit" />
              </ListItemButton>
            </ListItem>
          ) : null}
          {/* <ListItem disablePadding>
            <ListItemButton>
              <ListItemIcon>
                <SettingsIcon sx={{ color: 'white' }} />
              </ListItemIcon>
              <ListItemText primary="Menu1" />
            </ListItemButton>
          </ListItem> */}
          {/* <ListItem disablePadding>
            <ListItemButton>
              <ListItemIcon>
                <DashboardCustomizeIcon sx={{ color: 'white' }} />
              </ListItemIcon>
              <ListItemText primary="Menu2" />
            </ListItemButton>
          </ListItem> */}
        </List>
      </Box>
    </Paper>
  );
}
