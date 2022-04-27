import React from 'react';
import Paper from '@mui/material/Paper';
import Box from '@mui/material/Box';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import Divider from '@mui/material/Divider';
import InboxIcon from '@mui/icons-material/Inbox';
import DashboardIcon from '@mui/icons-material/Dashboard';
import DashboardCustomizeIcon from '@mui/icons-material/DashboardCustomize';
import SettingsIcon from '@mui/icons-material/Settings';
import { Link } from 'react-router-dom';

const linestyle = {
  height: '1px',
  backgroundImage:
    'linear-gradient(to right, rgba(255, 255, 255, 0), #ffffff, rgba(255, 255, 255, 0))',
};

export default function SideNavibar() {
  return (
    <Paper
      sx={{
        position: 'fixed',
        top: 24,
        left: 24,
        height: '95vh',
        width: 250,
        borderRadius: 5,
        background: 'linear-gradient(135deg, #4a4a4a, #5a5a5a)',
      }}
      elevation={3}
    >
      <Box sx={{ color: 'white', textAlign: 'center' }}>
        <List>
          <ListItem disablePadding>
            <Link to="/">
              <ListItemButton>
                <ListItemIcon>
                  <InboxIcon />
                </ListItemIcon>
                <ListItemText primary="Dockerby" />
              </ListItemButton>
            </Link>
          </ListItem>
        </List>
        <Divider light sx={linestyle} />
        <List>
          <ListItem disablePadding>
            <ListItemButton>
              <ListItemIcon>
                <DashboardIcon />
              </ListItemIcon>
              <ListItemText primary="Menu1" />
            </ListItemButton>
          </ListItem>
          <ListItem disablePadding>
            <ListItemButton>
              <ListItemIcon>
                <DashboardCustomizeIcon />
              </ListItemIcon>
              <ListItemText primary="Menu2" />
            </ListItemButton>
          </ListItem>
          <ListItem disablePadding>
            <Link to="/setting">
              <ListItemButton>
                <ListItemIcon>
                  <SettingsIcon />
                </ListItemIcon>
                <ListItemText primary="Menu3" />
              </ListItemButton>
            </Link>
          </ListItem>
        </List>
      </Box>
    </Paper>
  );
}
