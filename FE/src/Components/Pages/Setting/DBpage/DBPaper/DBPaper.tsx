import { useEffect, useState } from 'react';
import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';
import Typography from '@mui/material/Typography';
import SelectItem from 'Components/UI/Atoms/SelectItem/SelectItem';
import DeleteIcon from '@mui/icons-material/Delete';
import { DB } from 'Components/MDClass/DBData/DBData';
import { useDropdownStore } from 'Components/Store/DropDownStore/DropDownStore';
import { v4 as uuid } from 'uuid';
import axios from 'axios';

interface VersionTypeAxois {
  dbVersion: string[];
  properties: string[];
}

interface buildProps {
  index: number;
  dbData: DB;
  DelClick: (value: number) => void;
}

export default function DBPaper({ index, dbData, DelClick }: buildProps) {
  const dbItems = useDropdownStore((state) => state.db);

  const [port, setPort] = useState(dbData.port);
  const [name, setName] = useState(dbData.name);
  const [version, setVersion] = useState(dbData.version);
  const [dumpLocation, setDumpLocation] = useState(dbData.dumpLocation);

  const [db, setDB] = useState('');
  const [dbs, setDBs] = useState<string[]>([]);
  const [versions, setVersions] = dbData.version
    ? useState<string[]>([dbData.version])
    : useState<string[]>([]);
  const [properties, setProperties] = useState<string[]>(
    dbData.properties.map((value) => value.property),
  );
  const [propertiesData, setPropertiesData] = useState<string[]>(
    dbData.properties.map((value) => value.data),
  );

  const handleNameOnChange = (event: any) => {
    setName(event.target.value);
    dbData.name = event.target.value;
  };

  const handlePortOnChange = (event: any) => {
    setPort(event.target.value);
    dbData.port = event.target.value;
  };
  const handleDumpLocationOnChange = (event: any) => {
    setDumpLocation(event.target.value);
    dbData.dumpLocation = event.target.value;
  };

  const handleDelOnClick = () => {
    DelClick(index);
  };

  const handlePropsDBChange = (event: string) => {
    setDB(event);
    dbData.version = '';
    setVersion('');
  };

  const handlePropsVersionChange = (event: string) => {
    setVersion(event);
    dbData.version = event;
  };

  const handlePropsDBClick = (index: number) => {
    dbData.frameworkId = dbItems[index].id;

    const params = { typeId: dbData.frameworkId };
    axios
      .get('/api/project/dbVersion', { params })
      .then((res) => {
        const data = res.data as VersionTypeAxois;
        setVersion('');
        setVersions([...data.dbVersion]);
        setProperties([...data.properties]);
        setPropertiesData(data.properties.map(() => ''));
        dbData.properties = data.properties.map((value) => {
          return {
            data: '',
            property: value,
          };
        });
      })
      .catch();
  };

  useEffect(() => {
    const data = dbItems.map((value) => {
      if (value.id === dbData.frameworkId) setDB(value.name);
      return value.name;
    });
    setDBs(data);

    if (dbData.frameworkId !== 0) {
      const params = { typeId: dbData.frameworkId };
      axios
        .get('/api/project/dbVersion', { params })
        .then((res) => {
          const data = res.data as VersionTypeAxois;
          setVersions([...data.dbVersion]);

          if (!dbData.properties.length) {
            setProperties([...data.properties]);
            setPropertiesData(data.properties.map(() => ''));
            dbData.properties = data.properties.map((value) => {
              return {
                data: '',
                property: value,
              };
            });
          }
        })
        .catch();
    }

    return () => {
      setDBs([]);
      setVersions([]);
      setProperties([]);
    };
  }, []);

  return (
    <Box>
      <Grid container spacing={1}>
        <Grid item xs={2} sx={{ marginY: 'auto' }}>
          <Typography fontFamily="Noto Sans KR" fontSize={22}>
            명칭
          </Typography>
        </Grid>
        <Grid item xs={10}>
          <TextField
            fullWidth
            defaultValue={name}
            label="Name"
            InputLabelProps={{
              sx: { color: 'rgb(200,200,200)' },
            }}
            variant="outlined"
            size="small"
            sx={{ my: 1 }}
            placeholder="Name"
            InputProps={{ sx: { fontWeight: 'bold' } }}
            onChange={handleNameOnChange}
          />
        </Grid>
        <Grid item xs={2} sx={{ marginY: 'auto' }}>
          <Typography fontFamily="Noto Sans KR" fontSize={22}>
            데이터베이스
          </Typography>
        </Grid>
        <Grid item xs={10}>
          <SelectItem
            defaultValue={db}
            label="Framework/ Library"
            Items={dbs}
            Change={handlePropsDBChange}
            Click={handlePropsDBClick}
          />
        </Grid>
        <Grid item xs={2} sx={{ marginY: 'auto' }}>
          <Typography fontFamily="Noto Sans KR" fontSize={22}>
            버전
          </Typography>
        </Grid>
        <Grid item xs={10}>
          <SelectItem
            defaultValue={version}
            label="Versions"
            Items={versions}
            Change={handlePropsVersionChange}
          />
        </Grid>
        <Grid item xs={2} sx={{ marginY: 'auto' }}>
          <Typography fontFamily="Noto Sans KR" fontSize={22}>
            포트
          </Typography>
        </Grid>
        <Grid item xs={10}>
          <TextField
            label="Port"
            InputLabelProps={{
              sx: { color: 'rgb(200,200,200)' },
            }}
            fullWidth
            variant="outlined"
            size="small"
            sx={{ my: 1 }}
            placeholder="Port"
            defaultValue={port}
            InputProps={{ sx: { fontWeight: 'bold' } }}
            onChange={handlePortOnChange}
          />
        </Grid>
        <Grid item xs={2} sx={{ marginY: 'auto' }}>
          <Typography fontFamily="Noto Sans KR" fontSize={22}>
            덤프 파일 경로
          </Typography>
        </Grid>
        <Grid item xs={10}>
          <TextField
            label="Dump File Dir"
            InputLabelProps={{
              sx: { color: 'rgb(200,200,200)' },
            }}
            fullWidth
            variant="outlined"
            size="small"
            sx={{ my: 1 }}
            placeholder="Dump File Dir"
            defaultValue={dumpLocation}
            InputProps={{ sx: { fontWeight: 'bold' } }}
            onChange={handleDumpLocationOnChange}
          />
        </Grid>
        {properties.map((value, idx) => {
          return (
            <>
              <Grid item xs={2} key={uuid()} sx={{ marginY: 'auto' }}>
                <Box>
                  <Typography
                    fontFamily="Noto Sans KR"
                    fontSize={15}
                    sx={{ wordBreak: 'break-all' }}
                  >
                    {value}
                  </Typography>
                </Box>
              </Grid>
              <Grid item xs={10}>
                <TextField
                  label={value}
                  InputLabelProps={{
                    sx: { color: 'rgb(200,200,200)' },
                  }}
                  fullWidth
                  variant="outlined"
                  size="small"
                  sx={{ my: 1 }}
                  placeholder={value}
                  InputProps={{ sx: { fontWeight: 'bold' } }}
                  defaultValue={propertiesData[idx] ? propertiesData[idx] : ''}
                  onChange={(event: any) => {
                    propertiesData[idx] = event.target.value;
                    dbData.properties[idx].data = event.target.value;
                  }}
                />
              </Grid>
            </>
          );
        })}
        <Grid item xs={2}>
          <Button
            variant="outlined"
            startIcon={<DeleteIcon />}
            onClick={handleDelOnClick}
            sx={{
              color: 'red',
              borderColor: 'red',
            }}
          >
            삭제
          </Button>
        </Grid>
      </Grid>
    </Box>
  );
}
