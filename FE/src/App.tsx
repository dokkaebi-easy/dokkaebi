import React, { Suspense, lazy } from 'react';
import { Switch, Route } from 'react-router-dom';
import { useTheme, ThemeProvider, createTheme } from '@mui/material/styles';
import CssBaseline from '@mui/material/CssBaseline';
import { grey } from '@mui/material/colors';
import LoadingBar from 'Components/Pages/LoadingBar/LoadingBar';
import styled from 'styled-components';

const AccessLayoutPage = lazy(
  () => import('Components/Layouts/AccessLayout/AccessLayout'),
);

const HomeLayoutPage = lazy(
  () => import('Components/Layouts/BasicLayout/BasicLayout'),
);

export const ColorModeContext = React.createContext({
  // eslint-disable-next-line @typescript-eslint/no-empty-function
  toggleColorMode: () => {},
});

// 2. 팔레트에 새로운 색상에 대한 타입 정의
declare module '@mui/material/styles' {
  interface Palette {
    neutral: Palette['primary'];
  }
  interface PaletteOptions {
    neutral: PaletteOptions['primary'];
  }
}

// 3. Button 컴포넌트에 props로서 추가
declare module '@mui/material/Button' {
  interface ButtonPropsColorOverrides {
    neutral: true;
  }
}

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
              main: grey[50],
            }),
          },
          secondary: {
            main: grey[50],
            ...(mode === 'dark' && {
              main: grey[900],
            }),
          },
          neutral: {
            main: '#EAFCFF',
          },
          background: {
            default: grey[50],
            paper: grey[50],
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
                  primary: grey[50],
                  secondary: grey[200],
                }),
          },
        },
        typography: {
          fontFamily: [
            'Nunito',
            'Roboto',
            '"Helvetica Neue"',
            'Arial',
            'sans-serif',
            'Jua',
            'Tiro Devanagari Sanskrit',
            'Noto Sans KR',
          ].join(','),
        },
      }),
    [mode],
  );

  return (
    <ColorModeContext.Provider value={colorMode}>
      <ThemeProvider theme={theme}>
        <CssBaseline />
        <Suspense fallback={<LoadingBar />}>
          <Switch>
            <Route path="/access" component={AccessLayoutPage} />
            <Route path="/" component={HomeLayoutPage} />
          </Switch>
        </Suspense>
      </ThemeProvider>
    </ColorModeContext.Provider>
  );
}
export default App;

const Container = styled.div`
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: url(/assets/cool-background.png);
  background-size: cover;
`;

const ContainerLog = styled.div`
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: url(/assets/blurry-gradient-haikei.svg);
  background-size: cover;
`;
const ContainerWave = styled.div`
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: url(/assets/wave-haikei.svg);
  background-size: cover;
`;
