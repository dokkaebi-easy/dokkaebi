import React, { Suspense, lazy } from 'react';
import { Switch, Route } from 'react-router-dom';
import { useTheme, ThemeProvider, createTheme } from '@mui/material/styles';
import CssBaseline from '@mui/material/CssBaseline';
import { grey } from '@mui/material/colors';
import LodingBar from 'Components/Pages/LodingBar/LodingBar';

const LoginPage = lazy(() => import('Components/Pages/Login/Login'));
const SignUpPage = lazy(() => import('Components/Pages/SignUp/SignUp'));
const HomeLayoutPage = lazy(
  () => import('Components/Layouts/BasicLayout/BasicLayout'),
);

export const ColorModeContext = React.createContext({
  // eslint-disable-next-line @typescript-eslint/no-empty-function
  toggleColorMode: () => {},
});

function App() {
  const [mode, setMode] = React.useState<'light' | 'dark'>('light');
  const colorMode = React.useMemo(
    () => ({
      toggleColorMode: () => {
        setMode((prevMode) => (prevMode === 'light' ? 'dark' : 'light'));
      },
    }),
    [],
  );

  const theme = React.useMemo(
    () =>
      createTheme({
        palette: {
          mode,
          primary: {
            main: grey[900],
            ...(mode === 'dark' && {
              main: grey[300],
            }),
          },
          secondary: {
            main: 'rgb(240, 240, 240)',
            ...(mode === 'dark' && {
              main: grey[900],
            }),
          },
          background: {
            default: ' rgb(240, 240, 240)',
          },
          ...(mode === 'dark' && {
            background: {
              default: grey[900],
              paper: grey[900],
            },
          }),
          text: {
            ...(mode === 'light'
              ? {
                  primary: grey[900],
                  secondary: grey[800],
                }
              : {
                  primary: '#fff',
                  // secondary: grey[500],
                }),
          },
        },
      }),
    [mode],
  );

  return (
    <ColorModeContext.Provider value={colorMode}>
      <ThemeProvider theme={theme}>
        <CssBaseline />
        <Suspense fallback={<LodingBar />}>
          <Switch>
            <Route path="/signup" component={SignUpPage} exact />
            <Route path="/login" component={LoginPage} exact />
            <Route path="/" component={HomeLayoutPage} />
          </Switch>
        </Suspense>
      </ThemeProvider>
    </ColorModeContext.Provider>
  );
}
export default App;
