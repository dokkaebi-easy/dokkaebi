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
import { useContext, useEffect, useState } from 'react';
import FormGroup from '@mui/material/FormGroup';
import FormControlLabel from '@mui/material/FormControlLabel';
import Switch, { SwitchProps } from '@mui/material/Switch';
import { styled, useTheme } from '@mui/material/styles';
import { ColorModeContext } from 'App';
import DocumentScannerIcon from '@mui/icons-material/DocumentScanner';
import ArticleIcon from '@mui/icons-material/Article';

export default function SideNavibar() {
  const [pageName, setPageName] = useState('');
  const location = useLocation();

  const theme = useTheme();
  const colorMode = useContext(ColorModeContext);

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
        background:
          theme.palette.mode === 'dark'
            ? 'rgba(21,21,21,0.5)'
            : 'rgba(230,230,230,0.5)',
      }}
      elevation={3}
    >
      <Box sx={{ color: 'white', textAlign: 'center' }}>
        <List>
          <ListItem disablePadding alignItems="center">
            <Link
              to="/"
              style={{
                color: theme.palette.mode === 'dark' ? 'white' : 'black',
                textDecoration: 'none',
                width: '90%',
                margin: 'auto',
              }}
            >
              <ListItemButton>
                <ListItemIcon>
                  <img
                    src="/assets/dockerby_1.png"
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
          <ListItem disablePadding sx={{ marginLeft: '4px' }}>
            <Box
              style={{
                color: theme.palette.mode === 'dark' ? 'white' : 'black',
                textDecoration: 'none',
                width: '90%',
                margin: '0 auto',
                marginTop: 3,
                borderRadius: 5,
              }}
            >
              <FormControlLabel
                control={
                  <MaterialUISwitch
                    sx={{ m: 1 }}
                    defaultChecked={theme.palette.mode === 'dark'}
                    onChange={colorMode.toggleColorMode}
                  />
                }
                label={
                  theme.palette.mode === 'dark' ? 'Dark Mode' : 'Light Mode'
                }
              />
            </Box>
          </ListItem>
          <ListItem disablePadding>
            <a
              href="https://k6s205.p.ssafy.io"
              style={{
                color: theme.palette.mode === 'dark' ? 'white' : 'black',
                textDecoration: 'none',
                width: '90%',
                margin: '0 auto',
                marginTop: 3,
                borderRadius: 5,
              }}
              target="_blank"
              rel="noreferrer"
            >
              <ListItemButton>
                <ListItemIcon>
                  <ArticleIcon
                    sx={{
                      color: theme.palette.mode === 'dark' ? 'white' : 'black',
                    }}
                  />
                </ListItemIcon>
                <ListItemText primary="Document" />
              </ListItemButton>
            </a>
          </ListItem>
          <ListItem disablePadding>
            <Link
              to="/create"
              style={{
                color: theme.palette.mode === 'dark' ? 'white' : 'black',
                textDecoration: 'none',
                width: '90%',
                margin: '0 auto',
                marginTop: 3,
                borderRadius: 5,
              }}
            >
              <ListItemButton>
                <ListItemIcon>
                  <DashboardIcon
                    sx={{
                      color: theme.palette.mode === 'dark' ? 'white' : 'black',
                    }}
                  />
                </ListItemIcon>
                <ListItemText primary="Build Create" />
              </ListItemButton>
            </Link>
          </ListItem>
          {pageName === 'detail' ? (
            <ListItem disablePadding>
              <Box
                style={{
                  color: theme.palette.mode === 'dark' ? 'white' : 'black',
                  textDecoration: 'none',
                  width: '90%',
                  margin: '0 auto',
                  marginTop: 3,
                  borderRadius: 5,
                }}
              >
                <ListItemButton>
                  <ListItemIcon>
                    <SettingsIcon
                      sx={{
                        color:
                          theme.palette.mode === 'dark' ? 'white' : 'black',
                      }}
                    />
                  </ListItemIcon>
                  <ListItemText primary="Edit" />
                </ListItemButton>
              </Box>
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

const Clickstyle = {
  background: 'linear-gradient(45deg, #757F9A, #D7DDE8)',
};

const MaterialUISwitch = styled(Switch)(({ theme }) => ({
  width: 62,
  height: 34,
  padding: 7,
  '& .MuiSwitch-switchBase': {
    margin: 1,
    padding: 0,
    transform: 'translateX(6px)',
    '&.Mui-checked': {
      color: '#fff',
      transform: 'translateX(22px)',
      '& .MuiSwitch-thumb:before': {
        backgroundImage: `url('data:image/svg+xml;utf8,<svg xmlns="http://www.w3.org/2000/svg" height="20" width="20" viewBox="0 0 20 20"><path fill="${encodeURIComponent(
          '#fff',
        )}" d="M4.2 2.5l-.7 1.8-1.8.7 1.8.7.7 1.8.6-1.8L6.7 5l-1.9-.7-.6-1.8zm15 8.3a6.7 6.7 0 11-6.6-6.6 5.8 5.8 0 006.6 6.6z"/></svg>')`,
      },
      '& + .MuiSwitch-track': {
        opacity: 1,
        backgroundColor: theme.palette.mode === 'dark' ? '#8796A5' : '#aab4be',
      },
    },
  },
  '& .MuiSwitch-thumb': {
    backgroundColor: theme.palette.mode === 'dark' ? '#003892' : '#001e3c',
    width: 32,
    height: 32,
    '&:before': {
      content: "''",
      position: 'absolute',
      width: '100%',
      height: '100%',
      left: 0,
      top: 0,
      backgroundRepeat: 'no-repeat',
      backgroundPosition: 'center',
      backgroundImage: `url('data:image/svg+xml;utf8,<svg xmlns="http://www.w3.org/2000/svg" height="20" width="20" viewBox="0 0 20 20"><path fill="${encodeURIComponent(
        '#fff',
      )}" d="M9.305 1.667V3.75h1.389V1.667h-1.39zm-4.707 1.95l-.982.982L5.09 6.072l.982-.982-1.473-1.473zm10.802 0L13.927 5.09l.982.982 1.473-1.473-.982-.982zM10 5.139a4.872 4.872 0 00-4.862 4.86A4.872 4.872 0 0010 14.862 4.872 4.872 0 0014.86 10 4.872 4.872 0 0010 5.139zm0 1.389A3.462 3.462 0 0113.471 10a3.462 3.462 0 01-3.473 3.472A3.462 3.462 0 016.527 10 3.462 3.462 0 0110 6.528zM1.665 9.305v1.39h2.083v-1.39H1.666zm14.583 0v1.39h2.084v-1.39h-2.084zM5.09 13.928L3.616 15.4l.982.982 1.473-1.473-.982-.982zm9.82 0l-.982.982 1.473 1.473.982-.982-1.473-1.473zM9.305 16.25v2.083h1.389V16.25h-1.39z"/></svg>')`,
    },
  },
  '& .MuiSwitch-track': {
    opacity: 1,
    backgroundColor: theme.palette.mode === 'dark' ? '#8796A5' : '#aab4be',
    borderRadius: 20 / 2,
  },
}));

const linestyle = {
  height: '1px',
  backgroundImage:
    'linear-gradient(to right, rgba(255, 255, 255, 0), #ffffff, rgba(255, 255, 255, 0))',
};
